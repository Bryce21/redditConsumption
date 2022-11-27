#!/bin/bash
cat ../scala/resources/redditData/test.json | kafka-console-producer --bootstrap-server localhost:29092 --topic localDevTesting
