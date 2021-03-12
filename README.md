
<p align=center>
  <img src="Logo.png"/>
</p>

<h1 align=center>
SCHULATLAS
</h1>

<p align=center><i>To increase transparency within the education sector</i></p>

<p align=center>
  <img alt="Status Beta" src="https://img.shields.io/badge/Status-Draft-green.svg?style=flat"/>  <img alt="GPL v3" src="https://img.shields.io/badge/License-GPL%20v3-orange.svg"/>  <img alt="React" src="https://img.shields.io/badge/-React-blue?logo=react&style=flat"/>  <img alt="Java" src="https://img.shields.io/badge/-Java-brown?logo=java&style=flat"/>  <img alt="Spring" src="https://img.shields.io/badge/-Spring-lightgrey?logo=spring&style=flat"/>  <img alt="Docker" src="https://img.shields.io/badge/-Docker-grey?logo=docker&style=flat"/>  <img alt="MongoDb" src="https://img.shields.io/badge/-MongoDb-yellow?logo=mongodb&style=flat"/>
</p>

Among other things germany is known worldwide for its sustained success in the education sector. Thanks to the problem of the intertial system, however, this makes us locals perceive things a little differently. Unfortunately, old-fashioned federalism and a lack of transparency mean that traceability and comparability are more or less impossible for all sides.

**SCHULATLAS** addresses this problem, aggregates data from the provinces' quality management, processes it and outputs it both as overlays on maps and in a statistics module within the context of Open Data / Open Government. Data can be maintained via public API and accessed by anyone.

In this way, **SCHULATLAS** can hopefully contribute to the transition to a 21st century education for future generations to come.

### Features
- Website written in React with TypeScript to communicate the vision in a visually engaging way
- Backend written in Java with the help of Spring to aggregate, process and serve datasets
- Real-time processing of all datasets and create interactive custom map overlays to visualize the user-selected datapoints with the help of [HERE Studio](https://developer.here.com/products/platform/studio)
- Public REST API to maintain and access all gathered Datasets

### Contributing
**SCHULATLAS** is an open source project and contributions of any kind are welcome and highly appreciated. Issues, bugs and feature requests are all liested on the [issues](https://github.com/dride-for-sure/schulatlas/) page. Feel free to open a ticket and make feature requests. Have a look at the [CONTRIBUTING.md](CONTRIBUTING.md) to lean about the common style guide and project structure.

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


#### Seed data
To seed a local test environment mongodb intance please use the [`seed.js`](#) (*Upcoming*).

**SCHULATLAS** is an open source project and contributions of any kind are welcome and highly appreciated. Issues, bugs and feature requests are all liested on the [issues](https://github.com/dride-for-sure/schulatlas/issues) page. Feel free to open a ticket and make feature requests. Have a look at the [CONTRIBUTING.md](CONTRIBUTING.md) to lean about the common style guide and project structure.
**SCHULATLAS** is an open source project and contributions of any kind are welcome and highly appreciated. Issues, bugs and feature requests are all liested on the [issues](https://github.com/dride-for-sure/schulatlas/) page. Feel free to open a ticket and make feature requests. Have a look at the [CONTRIBUTING.md](CONTRIBUTING.md) to lean about the common style guide and project structure.

### License
SCHULATLAS is an open source project under the [GPLv3 License]("LICENSE")

Happy coding! :metal:
