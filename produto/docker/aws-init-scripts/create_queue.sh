#!/usr/bin/env bash

aws --version
export AWS_ACCESS_KEY_ID="test"
export AWS_SECRET_ACCESS_KEY="test"
export AWS_DEFAULT_REGION="us-east-1"
aws configure --profile localstack

aws sqs create-queue --endpoint-url http://localhost:4566 --queue-name order-update-status-queue --profile localstack
aws sqs create-queue --endpoint-url http://localhost:4566 --queue-name product-queue --profile localstack
aws sqs create-queue --endpoint-url http://localhost:4566 --queue-name logistic-queue --profile localstack