#!/bin/bash
cat ../scala/resources/redditData/justPost.json | kafka-console-producer --bootstrap-server localhost:29092 --topic localDevTesting
