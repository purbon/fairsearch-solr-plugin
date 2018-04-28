# Fair search algorithms for Elasticsearch


[![Build Status](https://travis-ci.org/fair-search/fairsearch-solr-plugin.svg?branch=master)](https://travis-ci.org/fair-search/fairsearch-elasticsearch-plugin)


The Fair search Solr plugin uses machine learning to provide a fair search result with relevant protected 
and non protected classes. 

# What this plugin does...

This plugin:

- Store fairness distribution tables to use during rescoring.
- Allows you to rescore fairly any query in elasticsearch.

## Where's the docs?

We recommend taking time to [read the docs](http://fairsearch-solr.readthedocs.io) (TBA). 

## How to contribute?

This plugin is an open source project and we love to receive contributions from the community — you! All contributions are welcome: ideas, patches, documentation, bug reports, complaints, and even something you drew up on a napkin.

Programming is not a required skill. Whatever you've seen about open source and maintainers or community members saying "send patches or die" - you will not see that here.

It is more important to me that you are able to contribute.

Extra bits at [CONTRIBUTING.md](CONTRIBUTTING.md)


# Installing

See the full list of [prebuilt versions](https://fair-search.github.io/). If you don't see a version available, see the link below for building.

TBA

# Development

Notes if you want to dig into the code or build for a version there's no build for.

### 1. Build with Maven

```
mvn clean test assembly:single
```

This runs the tasks that builds, tests, generates a Solr plugin jar file.

### 2. Install with in solr

TBA

# Who built this?

Initially developed by:
- Pere Urbón Bayes
- Tom Sühr

Special thanks to Meike Zehlike and Carlos Castillo, the minds behind the science in this plugin.

## Other Acknowledgments & Stuff To Read

- [FA*IR: A Fair Top-k Ranking Algorithm](https://arxiv.org/abs/1706.06368)