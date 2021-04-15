t<p align=center>
  <img src="Splashscreen.png" alt="Schulatlas Logo"/>
</p>
<br/>
<i><h3><p align=center>To increase transparency within the education sector</p></h3></i>
<br/>
Germany is known worldwide, among other things, for its sustained success in the education sector.
Unfortunately, federalism and a lack of transparency mean that traceability and comparability are
more or less impossible for all sides.

**SCHULATLAS** addresses this problem, aggregates data from the provinces' quality management,
processes it and outputs it both as overlays on maps and in a statistics module within the context
of Open Data / Open Government. Data can be maintained via public API and accessed by anyone.

In this way, **SCHULATLAS** can contribute to a successful transition toward an education system
that is well suited for the 21st century and for future generations to come.

### Features

- Public frontend website written in *React* to communicate the vision in a visually engaging way
- **Backend** written in **Java/Spring** to aggregate, process and serve datasets
- **Custom Headless CMS** written in *React* to maintain the numerous databases on the one hand and
	to fill the public frontend area with content on the other hand.
- **Indexed MongoDb** to make all datapoints available to the user in the frontend as quickly as
	possible
- Real-time processing of all datasets to create interactive **custom map overlays** to visualize
	the user-selected datapoints with the help of [Leaflet](https://leafletjs.com/)
- **Public REST API** to maintain and access all gathered Datasets in the context of **Open Data /
	Open Government**

### Design

This is the current state and to be seen as a work in progress. Feel free to contribute and improve this design:

<img src="/documentation/adobexd-mockup.png" alt="AdobeXD Mockup Collections"/>

### Contributors

<table>
	<tbody>
		<tr>
		<td align="center">
			<a href="https://www.github.com/dride-for-sure">
			<img src="https://avatars.githubusercontent.com/u/10451521?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Dennis Jauernig</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://github.com/Roman-devs">
			<img src="https://avatars.githubusercontent.com/u/68698213?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Roman Kuite</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://github.com/lisamittmann">
			<img src="https://avatars.githubusercontent.com/u/40956077?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Lisa Mittmann</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://github.com/jamarob">
			<img src="https://avatars.githubusercontent.com/u/48794499?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Jan Robert</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://github.com/Jothaka">
			<img src="https://avatars.githubusercontent.com/u/6401492?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Jan-Hendrik Kahle</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://github.com/christophersiem">
			<img src="https://avatars.githubusercontent.com/u/65181238?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Christopher Siem</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://github.com/fabianschmauder">
			<img src="https://avatars.githubusercontent.com/u/16700285?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Fabian Schmauder</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://github.com/NilsMester">
			<img src="https://avatars.githubusercontent.com/u/66268838?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Nils Mester</b></sub>
			</a>
		</td>
		</td>
	</tbody>
</table>

### Contributing

**SCHULATLAS** is an open source project. Contributions of any kind are welcome and highly
appreciated. Issues, bugs and feature requests are all listed on
the [issues](https://github.com/dride-for-sure/schulatlas/) page. Feel free to open a ticket and
make feature requests. Have a look at the [CONTRIBUTING.md](CONTRIBUTING.md) (*Upcoming*) to learn
about the common style guide and project structure.

#### Local Development

Clone the git repository and install the frontend via `npm`:

```
git clone git@github.com:dride-for-sure/schulatlas.git
cd schulatlas/frontend
npm i
```

To run the development server use:

```
npm start
```

To install the backend use `maven` and launch a local development server with the help of `spring`.

#### Internal API Architecture

This is an overview of all existing endpoints for the **SCHULATLAS** custom headless cms, frontend and map component. Details can be found in the API documentation and in the Postman Collections.

<img src="/documentation/internal-api-overview.png" alt="Api Overview"/>

#### External API References

- [AWS S3](https://www.aws.com)
- [Google Geocode API](https://developers.google.com/maps/documentation/geocoding/overview)
- [IP Geolocation API](https://geo.ipify.org)
- [Mapbox Custom Map Tiles](https://www.mapbox.com)

#### Postman Collections

To make local development easier, here are the postman collections as a starting point:

- [Postman Collection for Private Endpoints](/documentation/schulatlas-private-endpoints.postman_collection.json)
- [Postman Collection for Public Endpoints](/documentation/schulatlas-public-endpoints.postman_collection.json)

#### Seed data

To seed a local test environment mongodb instance please use the [`seed.js`](#) (*Upcoming*).

### Contributors

Many thanks go to all those wonderful people who reviewed my pull requests in detail and discovered one or the other blunder:

<table>
	<tbody>
		<tr>
		<td align="center">
<<<<<<< HEAD
=======
			<a href="https://www.github.com/dride-for-sure">
			<img src="https://avatars.githubusercontent.com/u/10451521?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Dennis Jauernig</b></sub>
			</a>
		</td>
		<td align="center">
>>>>>>> main
			<a href="https://github.com/Roman-devs">
			<img src="https://avatars.githubusercontent.com/u/68698213?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Roman Kuite</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://github.com/lisamittmann">
			<img src="https://avatars.githubusercontent.com/u/40956077?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Lisa Mittmann</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://github.com/jamarob">
			<img src="https://avatars.githubusercontent.com/u/48794499?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Jan Robert</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://github.com/Jothaka">
			<img src="https://avatars.githubusercontent.com/u/6401492?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Jan-Hendrik Kahle</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://github.com/christophersiem">
			<img src="https://avatars.githubusercontent.com/u/65181238?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Christopher Siem</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://github.com/fabianschmauder">
			<img src="https://avatars.githubusercontent.com/u/16700285?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Fabian Schmauder</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://github.com/NilsMester">
			<img src="https://avatars.githubusercontent.com/u/66268838?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Nils Mester</b></sub>
			</a>
		</td>
		<td align="center">
			<a href="https://www.github.com/dride-for-sure">
			<img src="https://avatars.githubusercontent.com/u/10451521?v=4" width="50" style="max-width:100%">
			<br/>
			<sub><b>Dennis Jauernig</b></sub>
			</a>
		</td>
		</tr>
	</tbody>
</table>

### License

SCHULATLAS is an open source project under the [GPLv3 License](LICENSE)

Happy coding! :metal:
<br/>
<br/>
<p>
<img alt="Status Beta" src="https://img.shields.io/badge/Status-Beta-green.svg?style=flat"/>  <img alt="GPL v3" src="https://img.shields.io/badge/License-GPL%20v3-orange.svg"/>  
<img alt="React" src="https://img.shields.io/badge/-React-blue?logo=react&style=flat"/>  
<img alt="Java" src="https://img.shields.io/badge/-Java-brown?logo=java&style=flat"/> 
<img alt="Spring" src="https://img.shields.io/badge/-Spring-lightgrey?logo=spring&style=flat"/>  
<img alt="Docker" src="https://img.shields.io/badge/-Docker-grey?logo=docker&style=flat"/>  
<img alt="MongoDb" src="https://img.shields.io/badge/-MongoDb-green?logo=mongodb&style=flat"/>
<img alt="AWS S3" src="https://img.shields.io/badge/-AWSS3-yellow?logo=amazon-aws&style=flat"/>
<img alt="Google Geocode API" src="https://img.shields.io/badge/-Google-silver?logo=google&style=flat"/>
<img alt="Backend TestCoverage" src="https://img.shields.io/badge/J--Unit%20Coverage-92%25-brightgreen&style=flat" />
</p>

