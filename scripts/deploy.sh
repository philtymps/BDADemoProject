#!/bin/bash

cp /opt/Sterling/runtime/jar/bda/18_3/BDADemoProject.jar "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/installedApps/aaNode01Cell/Sterling Applications.ear/."
cp /opt/Sterling/runtime/jar/bda/18_3/BDADemoProject.jar "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/installedApps/aaNode01Cell/Development.ear/."
/opt/Utilities/stopAll.sh
/opt/Utilities/startAll.sh

