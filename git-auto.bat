@echo off

call ant -f build.xml build

ping -n 5 127.0.0.1 > nul
