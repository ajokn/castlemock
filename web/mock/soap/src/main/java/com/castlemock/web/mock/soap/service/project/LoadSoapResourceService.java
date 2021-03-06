/*
 * Copyright 2017 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castlemock.web.mock.soap.service.project;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.soap.service.project.input.LoadSoapResourceInput;
import com.castlemock.core.mock.soap.service.project.output.LoadSoapResourceOutput;

/**
 * @author Karl Dahlgren
 * @since 1.16
 */
@org.springframework.stereotype.Service
public class LoadSoapResourceService extends AbstractSoapProjectService implements Service<LoadSoapResourceInput, LoadSoapResourceOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<LoadSoapResourceOutput> process(final ServiceTask<LoadSoapResourceInput> serviceTask) {
        final LoadSoapResourceInput input = serviceTask.getInput();
        final String resource = this.resourceRepository.loadSoapResource(input.getResourceId());
        return createServiceResult(LoadSoapResourceOutput.builder()
                .resource(resource)
                .build());
    }
}
