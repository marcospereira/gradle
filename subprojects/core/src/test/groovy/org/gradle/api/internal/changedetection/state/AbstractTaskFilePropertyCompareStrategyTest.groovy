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

package org.gradle.api.internal.changedetection.state

import com.google.common.collect.Lists
import org.gradle.api.internal.changedetection.rules.ChangeType
import org.gradle.api.internal.changedetection.rules.FileChange
import spock.lang.Specification

abstract class AbstractTaskFilePropertyCompareStrategyTest extends Specification {

    def changes(TaskFilePropertyCompareStrategy strategy, Map<String, IncrementalFileSnapshot> current, Map<String, IncrementalFileSnapshot> previous) {
        Lists.newArrayList(strategy.iterateContentChangesSince(current, previous, "test"))
    }

    def snapshot(boolean contentUpToDate = true) {
        return Mock(IncrementalFileSnapshot) {
            isContentUpToDate(_) >> { contentUpToDate }
            isContentAndMetadataUpToDate(_) >> { contentUpToDate }
        }
    }

    def change(String path, ChangeType type) {
        new FileChange(path, type, "test")
    }
}
