/*
 *  Copyright (C) 2018-2019 CARQS Consulting Private Limited
 *  All Rights Reserved.
 *  You may use, distribute and modify this code only under the
 *  terms of the 'e3NexT Software' license.
 *  You should have received a copy of the 'e3NexT Software' license
 *  without which you are not authorized to have access to or use
 *  this software in any capacity.
 *  For a copy of the license please write to: support@carqsconsulting.com
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.here.heremap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors
                        .any())
                .build();
    }
}
