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

package org.gradle.smoketests

import static AndroidPluginsSmokeTest.STABLE_ANDROID_2X_VERSION
import static AndroidPluginsSmokeTest.STABLE_ANDROID_3X_VERSION
import static org.gradle.smoketests.AndroidPluginsSmokeTest.assertAndroidHomeSet
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class KotlinPluginSmokeTest extends AbstractSmokeTest {
    // https://blog.jetbrains.com/kotlin/
    private kotlinVersion = '1.1.60'
    private androidBuildToolsVersion = AndroidPluginsSmokeTest.ANDROID_BUILD_TOOLS_VERSION

    def 'kotlin plugin'() {
        given:
        useSample("kotlin-example")
        replaceVariablesInBuildFile(kotlinVersion: this.kotlinVersion)

        when:
        def result = runner('run').forwardOutput().build()

        then:
        result.task(':compileKotlin').outcome == SUCCESS
    }

    def 'kotlin android plugin'() {
        given:
        assertAndroidHomeSet()
        useSample("android-kotlin-example")
        replaceVariablesInBuildFile(
            kotlinVersion: kotlinVersion,
            androidPluginVersion: androidPluginVersion,
            androidBuildToolsVersion: androidBuildToolsVersion)

        when:
        def build = runner('clean', 'testDebugUnitTestCoverage').build()

        then:
        build.task(':testDebugUnitTestCoverage').outcome == SUCCESS

        where:
        androidPluginVersion << [STABLE_ANDROID_2X_VERSION, STABLE_ANDROID_3X_VERSION]
    }
}
