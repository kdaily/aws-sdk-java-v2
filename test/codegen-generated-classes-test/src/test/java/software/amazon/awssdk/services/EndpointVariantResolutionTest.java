package software.amazon.awssdk.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.in;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.profiles.ProfileFile;
import software.amazon.awssdk.profiles.ProfileProperty;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.protocolrestjson.ProtocolRestJsonClient;
import software.amazon.awssdk.services.protocolrestjson.ProtocolRestJsonClientBuilder;
import software.amazon.awssdk.testutils.EnvironmentVariableHelper;
import software.amazon.awssdk.utils.StringInputStream;
import software.amazon.awssdk.utils.Validate;

public class EndpointVariantResolutionTest {
    @Test
    public void dualstackEndpointResolution() {
        EndpointCapturingInterceptor interceptor = new EndpointCapturingInterceptor();
        try {
            clientBuilder(interceptor).dualstackEnabled(true).build().allTypes();
        } catch (EndpointCapturingInterceptor.CaptureCompletedException e) {
            // Expected
        }

        assertThat(interceptor.endpoints())
            .singleElement()
            .isEqualTo("https://customresponsemetadata.us-west-2.api.aws/2016-03-11/allTypes");
    }

    @Test
    public void fipsEndpointResolution() {
        EndpointCapturingInterceptor interceptor = new EndpointCapturingInterceptor();
        try {
            clientBuilder(interceptor).fipsEnabled(true).build().allTypes();
        } catch (EndpointCapturingInterceptor.CaptureCompletedException e) {
            // Expected
        }

        assertThat(interceptor.endpoints())
            .singleElement()
            .isEqualTo("https://customresponsemetadata-fips.us-west-2.amazonaws.com/2016-03-11/allTypes");
    }

    @Test
    public void dualstackFipsEndpointResolution() {
        EndpointCapturingInterceptor interceptor = new EndpointCapturingInterceptor();
        try {
            clientBuilder(interceptor).dualstackEnabled(true).fipsEnabled(true).build().allTypes();
        } catch (EndpointCapturingInterceptor.CaptureCompletedException e) {
            // Expected
        }

        assertThat(interceptor.endpoints())
            .singleElement()
            .isEqualTo("https://customresponsemetadata-fips.us-west-2.api.aws/2016-03-11/allTypes");
    }

    private ProtocolRestJsonClientBuilder clientBuilder(EndpointCapturingInterceptor interceptor) {
        return ProtocolRestJsonClient.builder()
                                     .region(Region.US_WEST_2)
                                     .credentialsProvider(AnonymousCredentialsProvider.create())
                                     .overrideConfiguration(c -> c.addExecutionInterceptor(interceptor));
    }
}
