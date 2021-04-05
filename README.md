<p align=center>
  <img src="Logo.png"/>
</p>

<h1 align=center>
SCHULATLAS
</h1>

<p align=center><i>To increase transparency within the education sector</i></p>

<p align=center>
  <img alt="Status Beta" src="https://img.shields.io/badge/Status-Draft-green.svg?style=flat"/>  
<img alt="GPL v3" src="https://img.shields.io/badge/License-GPL%20v3-orange.svg"/>  
<img alt="React" src="https://img.shields.io/badge/-React-blue?logo=react&style=flat"/>  
<img alt="Java" src="https://img.shields.io/badge/-Java-brown?logo=java&style=flat"/> 
<img alt="Spring" src="https://img.shields.io/badge/-Spring-lightgrey?logo=spring&style=flat"/>  
<img alt="Docker" src="https://img.shields.io/badge/-Docker-grey?logo=docker&style=flat"/>  
<img alt="MongoDb" src="https://img.shields.io/badge/-MongoDb-green?logo=mongodb&style=flat"/>
<img alt="AWS S3" src="https://img.shields.io/badge/-AWSS3-yellow?logo=amazon-aws&style=flat"/>
<img alt="Google Geocode API" src="https://img.shields.io/badge/-Google-silver?logo=google&style=flat"/>
</p>

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
- Real-time processing of all datasets to create interactive custom map overlays to visualize the
	user-selected datapoints with the help of [Leaflet](https://leafletjs.com/)
- Public REST API to maintain and access all gathered Datasets in the context of Open Data / Open
	Government

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

#### Postman Collections

To make local development easier, here are the postman collections as a starting point:

- [Postman Collection for Private Endpoints](/documentation/schulatlas-private-endpoints.postman_collection.json)
- [Postman Collection for Public Endpoints](/documentation/schulatlas-public-endpoints.postman_collection.json)

#### Seed data

To seed a local test environment mongodb instance please use the [`seed.js`](#) (*Upcoming*).

### License

SCHULATLAS is an open source project under the [GPLv3 License](LICENSE)

Happy coding! :metal:
