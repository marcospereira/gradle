/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.internal.component.model;

import org.gradle.api.internal.attributes.AttributeContainerInternal;
import org.gradle.internal.DisplayName;

import java.util.List;

/**
 * Metadata for a basic variant of a component, that defines only artifacts and no dependencies.
 */
public interface VariantMetadata {
    String getName();

    DisplayName asDescribable();

    AttributeContainerInternal getAttributes();

    List<? extends ComponentArtifactMetadata> getArtifacts();
}
