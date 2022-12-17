#!/bin/bash

. "/spark/sbin/spark-config.sh"
. "/spark/bin/load-spark-env.sh"

mkdir -p $SPARK_WORKER_LOG

export SPARK_HOME=/part3.spark

ln -sf /dev/stdout $SPARK_WORKER_LOG/part3.spark-worker.out

/part3.spark/sbin/../bin/part3.spark-class org.apache.part3.spark.deploy.worker.Worker --webui-port $SPARK_WORKER_WEBUI_PORT $SPARK_MASTER >> $SPARK_WORKER_LOG/part3.spark-worker.out
