# Monica
Monica is a distributed file transport system. It provides the following features:
*   Load balancing
*   Routing according to IP 
*   Service registration and Discovery

  To get monica binaries, go to [maven centre](http://search.maven.org/#search%7Cga%7C1%7Cmonica). Here is an example to add dependency in Maven:
  
      <dependency>
        <groupId>com.polarcoral.monica</groupId>
        <artifactId>monica-framework</artifactId>
        <version>0.0.2-alpha</version>
      </dependency>
## Modules
*  monica-framework: APIS for SPI
*  monica-configuration: Server and client configuration APIs
*  monica-registry: Service registration and discovery
*  monica-cluster: Argorithms of loadbalancer and router
*  monica-coordinator: Coordinating the behaviours across modules
*  monica-files:  Protocolsets for the file transporting
*  monica-starter: Starting monica server and client quickly
*  monica-storage: Storage APIS of local disk or cloud
*  monica-examples: Examples
## Documentation
   [Monica wiki](https://github.com/polarcoral/monica/wiki/Getting-started)
## License
  Copyright 2017 The Monica Project
 
  The Monica Project licenses this file to you under the Apache License,
  version 2.0 (the "License"); you may not use this file except in compliance
  with the License. You may obtain a copy of the License at:  <br />
   
   [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)  <br />
 
 Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations
  under the License.
  
## Questions
   Email lucy@polarcoral.com
