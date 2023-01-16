## Práctica#1
- Descripción
  - Debe crearse una API que centralice otros servicios de búsqueda para que quién consuma
el servicio tenga un solo endpoint en donde coloque su criterio de búsqueda y esta API
realice búsquedas en otros servicios, las consolide, las ordene y les coloque un solo formato
de resultado indicando cuál es el origen en donde se encontró la coincidencia.

- Origen de datos:
  - API iTunes: https://developer.apple.com/library/archive/documentation/AudioVideo/Conceptual/iTuneSearchAPI/
  - API TVmaze: https://www.tvmaze.com/api
  
- Resultados:
  - Con el controlador ubicado en el archivo RestService utilizar: http://localhost:8686/rest/find/{nombre}
  - Con el controlador ubicado en el archivo RestServiceV2 utilizar: http://localhost:8686/rest/find/{nombre}/{limit}
