/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.awssdk.services.s3.internal.endpoints;

import java.net.URI;
import java.util.Objects;
import software.amazon.awssdk.annotations.SdkInternalApi;
import software.amazon.awssdk.core.SdkRequest;
import software.amazon.awssdk.http.SdkHttpRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Configuration;

/**
 * Contains the information needed to resolve S3 endpoints.
 */
@SdkInternalApi
public final class S3EndpointResolverContext {
    private final SdkHttpRequest request;
    private final SdkRequest originalRequest;
    private final Region region;
    private final S3Configuration serviceConfiguration;
    private final URI endpointOverride;
    private final boolean disableHostPrefixInjection;

    private S3EndpointResolverContext(Builder builder) {
        this.request = builder.request;
        this.originalRequest = builder.originalRequest;
        this.region = builder.region;
        this.serviceConfiguration = builder.serviceConfiguration;
        this.endpointOverride = builder.endpointOverride;
        this.disableHostPrefixInjection = builder.disableHostPrefixInjection;
    }

    public static Builder builder() {
        return new Builder();
    }

    public SdkHttpRequest request() {
        return request;
    }

    public SdkRequest originalRequest() {
        return originalRequest;
    }

    public Region region() {
        return region;
    }

    public S3Configuration serviceConfiguration() {
        return serviceConfiguration;
    }

    public URI endpointOverride() {
        return endpointOverride;
    }

    public boolean isDisableHostPrefixInjection() {
        return disableHostPrefixInjection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        S3EndpointResolverContext that = (S3EndpointResolverContext) o;
        return Objects.equals(endpointOverride, that.endpointOverride) &&
               Objects.equals(request, that.request) &&
               Objects.equals(originalRequest, that.originalRequest) &&
               Objects.equals(region, that.region) &&
               Objects.equals(serviceConfiguration, that.serviceConfiguration) &&
               (disableHostPrefixInjection == that.disableHostPrefixInjection);
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + Objects.hashCode(request());
        hashCode = 31 * hashCode + Objects.hashCode(originalRequest());
        hashCode = 31 * hashCode + Objects.hashCode(region());
        hashCode = 31 * hashCode + Objects.hashCode(serviceConfiguration());
        hashCode = 31 * hashCode + Objects.hashCode(endpointOverride());
        hashCode = 31 * hashCode + Objects.hashCode(isDisableHostPrefixInjection());
        return hashCode;
    }

    public Builder toBuilder() {
        return builder().endpointOverride(endpointOverride)
                        .request(request)
                        .originalRequest(originalRequest)
                        .region(region)
                        .serviceConfiguration(serviceConfiguration);
    }

    public static final class Builder {
        private SdkHttpRequest request;
        private SdkRequest originalRequest;
        private Region region;
        private S3Configuration serviceConfiguration;
        private URI endpointOverride;
        private boolean disableHostPrefixInjection;

        private Builder() {
        }

        public Builder request(SdkHttpRequest request) {
            this.request = request;
            return this;
        }

        public Builder originalRequest(SdkRequest originalRequest) {
            this.originalRequest = originalRequest;
            return this;
        }

        public Builder region(Region region) {
            this.region = region;
            return this;
        }

        public Builder serviceConfiguration(S3Configuration serviceConfiguration) {
            this.serviceConfiguration = serviceConfiguration;
            return this;
        }

        public Builder endpointOverride(URI endpointOverride) {
            this.endpointOverride = endpointOverride;
            return this;
        }

        public Builder disableHostPrefixInjection(boolean disableHostPrefixInjection) {
            this.disableHostPrefixInjection = disableHostPrefixInjection;
            return this;
        }

        public S3EndpointResolverContext build() {
            return new S3EndpointResolverContext(this);
        }
    }
}
