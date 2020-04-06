Vue.filter('format-thousands', function (value) {
  // https://stackoverflow.com/a/2901298
  return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
});


new Vue({
  el: "#app",
  data: {
    textSearch: "",
    statistics: []
  },
  computed: {
     countriesFilter: function() {
       var textSearch = this.textSearch;
       return this.statistics.filter(function(el) {
         return el.country.toLowerCase().indexOf(textSearch.toLowerCase()) !== -1;
       });
     }
  },
  created: function() {
    var that = this;
    axios.get('https://cors-anywhere.herokuapp.com/https://statistics-coronavirus.herokuapp.com/api/v1/statistics-covid-19/stats')
    .then(function (response) {
      that.statistics = JSON.parse(response.request.response).response.data.covid19Stats;
    })
    .catch(function (error) {
      console.log(error);
    });
  },
  mounted: function() {
    $.scrollUp();
  }
})