# COVID Data

This project aims to provide two modules. A batch module to import publicly available data to a local database, and a REST module to analyze the data.

## Prerequisites

* MariaDB

## Data

* [JHU Data (GitHub link)](https://github.com/CSSEGISandData/COVID-19/tree/master/csse_covid_19_data) - Clone this to your local
This data is in CSV format and there are lots of changes in the format of the file and date fields. Currently, data after March 23rd can be imported.
* **_Watch this space for more supported sources_**

## Future Plans

* Importer support for other data sources and formats
* A complete UI and REST services to provide various statistical analysis of the data.
* State-wise and district-wise data for India
* Once patient metadata like age, pre-existing conditions etc are available, a deeper analysis based on those parameters.
* If possible a statistical analysis for drugs and vaccine test data.

## Authors

* [David P Shenba](https://github.com/davidshenba)

## License

This project is licensed under the [GNU GLP License](https://www.gnu.org/licenses/gpl-3.0.en.html).

## Acknowledgments

* [Johns Hopkins University - Coronavirus Resource Center](https://coronavirus.jhu.edu/)

