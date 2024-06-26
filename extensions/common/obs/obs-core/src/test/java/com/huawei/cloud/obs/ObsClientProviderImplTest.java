/*
 *  Copyright (c) 2024 Huawei Technologies
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Huawei Technologies - initial API and implementation
 *
 */

package com.huawei.cloud.obs;

import com.obs.services.BasicObsCredentialsProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ObsClientProviderImplTest {

    private final ObsClientProviderConfiguration configuration = ObsClientProviderConfiguration.Builder.newInstance()
            .iamEndpoint("http://test")
            .credentialsProvider(new BasicObsCredentialsProvider("ak", "sk"))
            .build();
    private ObsClientProviderImpl clientProvider;

    @BeforeEach
    void setup() {
        clientProvider = new ObsClientProviderImpl(configuration, mock());
    }

    @Test
    void obsClient() {
        var client = clientProvider.obsClient("http://test");
        assertThat(client).isNotNull();

        var sameClient = clientProvider.obsClient("http://test");

        assertThat(sameClient).isEqualTo(client);

        var secondClient = clientProvider.obsClient("http://test1");

        assertThat(secondClient).isNotEqualTo(client);

    }

    @Test
    void iamClient() {
        var client = clientProvider.iamClient();
        assertThat(client).isNotNull();

    }

    @Test
    void shutdownProvider() {
        clientProvider.obsClient("http://test");
        clientProvider.shutdown();
    }
}
