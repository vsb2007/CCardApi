function get() {

    #NEXUS_RAW_URL="https://nexus.service.swordfishsecurity.com/repository/gpb-maven-release"
    NEXUS_RAW_URL="https://nexus.service.swordfishsecurity.com/repository/hub-raw-stage"
    NEXUS_RAW_URL_RELEASE="https://nexus.service.swordfishsecurity.com/repository/hub-raw-release"
    NEXUS_USR="gitlab"
    NEXUS_PSW="ZFQQ7rj2AaM3f7D"
    user="vbaranov-sfs:VDj246Q8QDHYP4PNkF5C"

    #subdomain="$1"
    subdomain="r1-4-rc2"
    echo "${subdomain//[\/,\-,_,[a-zA-Z]]/.}"
    exit 0
    array_tool=(appsec-cli appsec-hub checkmarx-cli klar cloc nexus-iq-cli)
    arch_teamcity="hub-ci-integration-teamcity"

    echo "start..."

    folder="./tmp"
    rm -rf $folder/*
    rm -rf integrations-teamcity
    rm -rf ${arch_teamcity}*.zip

    mkdir -p $folder/tools
    for tool in ${array_tool[*]}; do
        echo "tool: ${tool}-${subdomain}.zip"
        tool_url="$NEXUS_RAW_URL/${tool}-${subdomain}.zip"
        curl --user $NEXUS_USR:"$NEXUS_PSW" $tool_url --output $folder/tools/${tool}.zip || exit 1
    done
    git clone https://$user@bitbucket.org/swdfsh/integrations-teamcity.git || exit 1
    cp -r integrations-teamcity/meta-runners $folder || exit 1
    ls -al $folder

    cd tmp || exit 1
    #regular
    arh_teamcity_fullname="${arch_teamcity}-${subdomain}.zip"
    zip -r ../${arh_teamcity_fullname} ./* || exit 1
    cd .. || exit 1
    curl --user "$NEXUS_USR:$NEXUS_PSW" --upload-file ./${arh_teamcity_fullname} "${NEXUS_RAW_URL_RELEASE}/${arh_teamcity_fullname}" || exit 1

    rm -rf $folder/*
    rm -rf integrations-teamcity
    rm -rf ${arch_teamcity}*.zip

}

get $1
