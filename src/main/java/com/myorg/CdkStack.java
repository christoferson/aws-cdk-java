package com.myorg;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.s3.BlockPublicAccess;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketEncryption;
import software.amazon.awscdk.services.s3.BucketProps;
import software.amazon.awscdk.services.s3.ObjectOwnership;
import software.amazon.awscdk.services.sqs.Queue;
import software.constructs.Construct;

public class CdkStack extends Stack {
    public CdkStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public CdkStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // The code that defines your stack goes here

        Bucket bucket = new Bucket(this, "MyBucket", new BucketProps.Builder()
                .versioned(false)
                .encryption(BucketEncryption.KMS_MANAGED)
                .blockPublicAccess(BlockPublicAccess.BLOCK_ALL)
                .enforceSsl(true)
                .objectOwnership(ObjectOwnership.BUCKET_OWNER_ENFORCED)
                .build());        
        
         final Queue queue = Queue.Builder.create(this, "CdkQueue")
                 .visibilityTimeout(Duration.seconds(300))
                 .build();
    }
}
