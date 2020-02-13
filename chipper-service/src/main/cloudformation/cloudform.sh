#/bin/bash

export CURRENT_DIR="$(pwd)"
export SCRIPT_DIR="$(dirname $0)"
export TEMPLATE_BUCKET="leidoslabs/cf-templates/chipper-service-$USERNAME"
export TEMPLATE_URL="s3://$TEMPLATE_BUCKET"
export REGION=us-east-1
export KEY_NAME=advanced-analytics-05-18-2017
export MAX_CHIPPERS="$1"
export STACK_SUFFIX="${2:-0}"
export PRIMARY_STACK="${3:-false}"
export STACK_NAME=chipserver-$STACK_SUFFIX

# $SCRIPT_DIR/../../../buildAll.sh clean install "-Dmaven.test.skip=true"

aws s3 sync $SCRIPT_DIR $TEMPLATE_URL

aws s3 sync --exclude "*" --include chipserver.jar ../../../target $TEMPLATE_URL 
aws s3 sync --exclude "*" --include log4j.properties ../../../src/main/resources $TEMPLATE_URL
aws s3 sync --exclude "*" --include downloadOffline.sh ../../../../elt $TEMPLATE_URL

aws cloudformation create-stack --stack-name $STACK_NAME \
    --template-url https://s3.amazonaws.com/$TEMPLATE_BUCKET/chipserver.template \
    --capabilities CAPABILITY_NAMED_IAM CAPABILITY_IAM \
    --disable-rollback \
    --region $REGION \
    --parameters ParameterKey=DeploymentArtifactsBucketName,ParameterValue=$TEMPLATE_BUCKET \
                 ParameterKey=KeyName,ParameterValue=$KEY_NAME \
                 ParameterKey=StackSuffix,ParameterValue=$STACK_SUFFIX \
                 ParameterKey=PrimaryStack,ParameterValue=$PRIMARY_STACK \
                 ParameterKey=MaxChippers,ParameterValue=$MAX_CHIPPERS

cd $CURRENT_DIR
