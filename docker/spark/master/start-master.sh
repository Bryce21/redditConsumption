#!/bin/bash

export SPARK_MASTER_HOST=`hostname`

. "/spark/sbin/spark-config.sh"

. "/spark/bin/load-spark-env.sh"

mkdir -p $SPARK_MASTER_LOG

export SPARK_HOME=/part3.spark

ln -sf /dev/stdout $SPARK_MASTER_LOG/part3.spark-master.out

cd /part3.spark/bin && /part3.spark/sbin/../bin/part3.spark-class org.apache.part3.spark.deploy.master.Master --ip $SPARK_MASTER_HOST --port $SPARK_MASTER_PORT --webui-port $SPARK_MASTER_WEBUI_PORT >> $SPARK_MASTER_LOG/part3.spark-master.out
