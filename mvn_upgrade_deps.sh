#!/usr/bin/bash

set -o pipefail

# TODO: You may have to add filters to your project to disregard some versions

help() {
    echo "This script will:"
    echo "* update maven dependencies to the latest versions"
    echo "* run the maven tests"
    echo "* if tests pass, create a new git branch and push it to bitbucket"
    echo
    echo "Syntax: mvn_upgrade_deps.sh [-h|l|s|r]"
    echo "options:"
    echo "h     Print this Help."
    echo "l     Local git branch only. It won't push to bitbucket"
    echo "s     Maven settings file"
    echo "r     Maven version rules file - TBD"
    echo "x     Turn on bash debugging"
    echo
    echo "Example: mvn_upgrade_deps.sh -l -s settings.xml -r rules.xml"
    exit 1
}

# Use <https://google.github.io/styleguide/shellguide.html> for developing the script

MVN_OPTIONS="-q"
VERSIONS_RULES_FILE="TODO"
LOCAL_ONLY=0

while getopts ":hls:r:x" optionName; do
    case "${optionName}" in
        h) help;;
        l) LOCAL_ONLY=1;;
        s) MVN_OPTIONS="${MVN_OPTIONS} --settings ${OPTARG}";;
        r) VERSIONS_RULES_FILE="${OPTARG}";;
        x) set -x;;
        :) echo "Missing argument on -${OPTARG}"; help;;
        [?]) echo "Unknown option"; help;;
    esac
done

ts=`date +%s`
echo "Checking for maven updates. Timestamp ${ts}"

ORIG_BRANCH=`git branch --show-current`

VERSIONS_CHANGES_FILE="target/versions-changes.xml"
CHANGES_FILE="changes/${ts}_mvn-changes.xml"
AUTO_UPDATE_BRANCH_NAME="feature/auto-update-${ts}"

# Add settings to all mvn commands
mvn() {
    command mvn ${MVN_OPTIONS} "$@"
}

copy_change_file() {
    if [ -x "$(command -v xmllint)" ]; then
        xmllint --format "${VERSIONS_CHANGES_FILE}" --output "$1"
    else
        cp "${VERSIONS_CHANGES_FILE}" "$1"
    fi
}

clean_up() {
    # Revert back to orginal branch 
    git checkout "${ORIG_BRANCH}"
}

check_new_versions() {
    local output=`mvn test --fail-at-end 2>&1`
    # grep -v as we want to status to be 0 if FAILURE is not found
    if [ $? -ne 0 ] || echo "${output}" | grep -vq "FAILURE"; then
        mvn versions:revert
        exit 1
    else
        mvn versions:commit
    fi
}

# Only format "xml" is supported
mvn clean help:effective-settings versions:use-latest-releases -DchangeRecorderFormat=xml
if [ $? -ne 0 ]; then
    echo "Maven failed running versions:use-latest-releases"
else
    if [ -f "${VERSIONS_CHANGES_FILE}" ]; then
        check_new_versions

        mkdir --parents changes

        copy_change_file "${CHANGES_FILE}"

        git checkout -b "${AUTO_UPDATE_BRANCH_NAME}"
        git add pom.xml "${CHANGES_FILE}"

        # Pipe the changes into the git commit descripion. Mind the ( and { brackets
        git commit --message="Automated dependency update" --message="$(<${CHANGES_FILE})"
        if [ "${LOCAL_ONLY}" -ne 1 ]; then
            git push --set-upstream origin "${AUTO_UPDATE_BRANCH_NAME}"
        fi

        clean_up
    else
        echo "No changes detected"
    fi
fi