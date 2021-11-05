#!/bin/sh

mkdir -p ~/.m2
echo "
        <settings xmlns=\"http://maven.apache.org/SETTINGS/1.0.0\"
          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"
          xsi:schemaLocation=\"http://maven.apache.org/SETTINGS/1.0.0
                      https://maven.apache.org/xsd/settings-1.0.0.xsd\">
        <servers>
            <server>
              <id>maven-proxy</id>
              <username>$NEXUS_USERNAME</username>
              <password>$NEXUS_PASSWORD</password>
            </server>
          </servers>
      </settings>

    " >~/.m2/settings.xml
