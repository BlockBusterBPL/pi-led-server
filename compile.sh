#!/bin/bash
mvn clean compile validate assembly:single > build.log
