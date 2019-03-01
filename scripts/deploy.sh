#!/bin/bash
/opt/Sterling/runtime/bin/deployer.sh -t resourcejar
cp /opt/Sterling/runtime/jar/platform/9_5/resources.jar "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/installedApps/aaNode01Cell/Sterling Applications.ear/."
cp /opt/Sterling/runtime/jar/platform/9_5/resources.jar "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/installedApps/aaNode01Cell/Development.ear/."
cp /opt/Sterling/runtime/jar/bda/19_1/BDADemoProject.jar "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/installedApps/aaNode01Cell/Sterling Applications.ear/."
cp /opt/Sterling/runtime/jar/bda/19_1/BDADemoProject.jar "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/installedApps/aaNode01Cell/Development.ear/."
/opt/Utilities/stopAll.sh
/opt/Utilities/startAll.sh

