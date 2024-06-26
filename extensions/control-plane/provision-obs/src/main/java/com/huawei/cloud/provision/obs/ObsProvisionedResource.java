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

package com.huawei.cloud.provision.obs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.huawei.cloud.obs.ObsBucketSchema;
import org.eclipse.edc.connector.controlplane.transfer.spi.types.ProvisionedDataDestinationResource;

import static com.huawei.cloud.obs.ObsBucketSchema.BUCKET_NAME;
import static com.huawei.cloud.obs.ObsBucketSchema.ENDPOINT;
import static org.eclipse.edc.spi.constants.CoreConstants.EDC_NAMESPACE;


@JsonDeserialize(builder = ObsProvisionedResource.Builder.class)
@JsonTypeName("dataspaceconnector:obsbucketprovisionedresource")
public class ObsProvisionedResource extends ProvisionedDataDestinationResource {

    private ObsProvisionedResource() {
    }

    public String getEndpoint() {
        return getDataAddress().getStringProperty(ENDPOINT);
    }

    public String getBucketName() {
        return getDataAddress().getStringProperty(BUCKET_NAME);
    }

    @Override
    public String getResourceName() {
        return dataAddress.getStringProperty(BUCKET_NAME);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder extends ProvisionedDataDestinationResource.Builder<ObsProvisionedResource, Builder> {

        private Builder() {
            super(new ObsProvisionedResource());
            dataAddressBuilder.type(ObsBucketSchema.TYPE);
        }

        @JsonCreator
        public static Builder newInstance() {
            return new Builder();
        }

        public Builder endpoint(String endpoint) {
            dataAddressBuilder.property(EDC_NAMESPACE + ENDPOINT, endpoint);
            return this;
        }

        public Builder bucketName(String bucketName) {
            dataAddressBuilder.property(EDC_NAMESPACE + BUCKET_NAME, bucketName);
            dataAddressBuilder.keyName("obs-temp-" + bucketName);
            return this;
        }

    }

}
