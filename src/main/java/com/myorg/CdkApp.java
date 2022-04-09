package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.DefaultStackSynthesizer;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class CdkApp {
    public static void main(final String[] args) {
        App app = new App();

        new CdkStack(app, "CdkStack", StackProps.builder()
                // If you don't specify 'env', this stack will be environment-agnostic.
                // Account/Region-dependent features and context lookups will not work,
                // but a single synthesized template can be deployed anywhere.

                // Uncomment the next block to specialize this stack for the AWS Account
                // and Region that are implied by the current CLI configuration.

                .env(Environment.builder()
                        .account(System.getenv("CDK_DEFAULT_ACCOUNT"))
                        .region(System.getenv("CDK_DEFAULT_REGION"))
                        .build())

                // Uncomment the next block if you know exactly what Account and Region you
                // want to deploy the stack to.
                /*
                .env(Environment.builder()
                        .account("123456789012")
                        .region("us-east-1")
                        .build())
                */

        		//.synthesizer(newSynthesizer())
        		
                // For more information, see https://docs.aws.amazon.com/cdk/latest/guide/environments.html
                .build());

        app.synth();
    }
    
    private static DefaultStackSynthesizer newSynthesizer() {
    	
    	return DefaultStackSynthesizer.Builder.create()
    			
    	//.qualifier("M")
    	
        // Name of the S3 bucket for file assets
        .fileAssetsBucketName("cdk-${Qualifier}-assets-${AWS::AccountId}-${AWS::Region}")
        .bucketPrefix("")

        // Name of the ECR repository for Docker image assets
        .imageAssetsRepositoryName("cdk-${Qualifier}-container-assets-${AWS::AccountId}-${AWS::Region}")

        // ARN of the role assumed by the CLI and Pipeline to deploy here
        .deployRoleArn("arn:${AWS::Partition}:iam::${AWS::AccountId}:role/cdk-${Qualifier}-deploy-role-${AWS::AccountId}-${AWS::Region}")
        .deployRoleExternalId("")

        // ARN of the role used for file asset publishing (assumed from the deploy role)
        .fileAssetPublishingRoleArn("arn:${AWS::Partition}:iam::${AWS::AccountId}:role/cdk-${Qualifier}-file-publishing-role-${AWS::AccountId}-${AWS::Region}")
        .fileAssetPublishingExternalId("")

        // ARN of the role used for Docker asset publishing (assumed from the deploy role)
        .imageAssetPublishingRoleArn("arn:${AWS::Partition}:iam::${AWS::AccountId}:role/cdk-${Qualifier}-image-publishing-role-${AWS::AccountId}-${AWS::Region}")
        .imageAssetPublishingExternalId("")

        // ARN of the role passed to CloudFormation to execute the deployments
        .cloudFormationExecutionRole("arn:${AWS::Partition}:iam::${AWS::AccountId}:role/cdk-${Qualifier}-cfn-exec-role-${AWS::AccountId}-${AWS::Region}")

        // Name of the SSM parameter which describes the bootstrap stack version number
        .bootstrapStackVersionSsmParameter("/cdk-bootstrap/${Qualifier}/version")

        // Add a rule to every template which verifies the required bootstrap stack version
        .generateBootstrapVersionRule(true)
        
        .build();

    	
    }
}

