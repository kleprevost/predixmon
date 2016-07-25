class apmSampleAppCtrl {
  constructor($scope, $rootScope, $state, $timeout, ApmSampleAppService, getAssets, $http) {
    $rootScope.tenantInfo = null;
    $rootScope.userPreferenceForAssetName = null;
    $scope.assets = getAssets;
    $scope.selected = "tab1";

    $http({
      method: 'GET',
      //url: 'https://apm-timeseries-services-hackapm.run.aws-usw02-pr.ice.predix.io/v2/time_series?operation=raw&tagList=POKE5-CASINO-COSMOPOLITAN-PM_UNIT.POKE5-CASINO-COSMOPOLITAN-PM_UNIT-LONGITUDE&startTime=2015-12-31T00:28:03.000Z',
      url: 'test',
      headers: {
        'Authorization':  'bearer eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIxYjVmYjY1MC0wZjI3LTRmMzktYTI5OC1iOTlhZTZmZjk1MjUiLCJzdWIiOiIwYWVjYmRiZi1jOGMzLTRiNjItYmM5Yy01ZjE2ZDhlYzNkZTciLCJzY29wZSI6WyJwYXNzd29yZC53cml0ZSIsIm9wZW5pZCJdLCJjbGllbnRfaWQiOiJpbmdlc3Rvci45Y2YzM2NlMzdiZjY0YzU2ODFiNTE1YTZmNmFhZGY0NyIsImNpZCI6ImluZ2VzdG9yLjljZjMzY2UzN2JmNjRjNTY4MWI1MTVhNmY2YWFkZjQ3IiwiYXpwIjoiaW5nZXN0b3IuOWNmMzNjZTM3YmY2NGM1NjgxYjUxNWE2ZjZhYWRmNDciLCJncmFudF90eXBlIjoicGFzc3dvcmQiLCJ1c2VyX2lkIjoiMGFlY2JkYmYtYzhjMy00YjYyLWJjOWMtNWYxNmQ4ZWMzZGU3Iiwib3JpZ2luIjoidWFhIiwidXNlcl9uYW1lIjoicG9rZW1vbiIsImVtYWlsIjoiZGF2aWQuc3RlaW5iZXJnZXJAZ2UuY29tIiwiYXV0aF90aW1lIjoxNDY5NDIzNTIzLCJyZXZfc2lnIjoiYmRiZTQ5YWYiLCJpYXQiOjE0Njk0MjM1MjMsImV4cCI6MTQ2OTUwOTkyMywiaXNzIjoiaHR0cHM6Ly9kOWVmMTA2Yy03MDQ4LTQ4NmUtYTc5Zi05YzgwODI3YjhhMTQucHJlZGl4LXVhYS5ydW4uYXdzLXVzdzAyLXByLmljZS5wcmVkaXguaW8vb2F1dGgvdG9rZW4iLCJ6aWQiOiJkOWVmMTA2Yy03MDQ4LTQ4NmUtYTc5Zi05YzgwODI3YjhhMTQiLCJhdWQiOlsiaW5nZXN0b3IuOWNmMzNjZTM3YmY2NGM1NjgxYjUxNWE2ZjZhYWRmNDciLCJwYXNzd29yZCIsIm9wZW5pZCJdfQ.lFD6bHFJXuZH2Awxatp1MvHVDUHNTUpTckrXNASWeYfz6GV5DDWe9WIGYzTyTrN5UiaFi95MwzVrNR9OGh59VAQ-vvBpo5G7WlG8HILJHhBPgMSXSYtNf4qgMzwHPay5RqVmurZVZ8D4xiy9ZBWOSCZqnOtIEt25VA_hMhI8wPvXGHkU79YqbpLpyco6WACQ-IxrfVRB1EJvZnoZz4JIG84iDrfbt4pVPhbSYvrJ_kzyRX71NcYhNcED4dNYEftCQxxUBHkOcuXxcpAvJWJSEeaQAjELQ6LU7hMO5HmPc0Ztip2yl-2n0etL-0V73ly0udAZdmqb4Wm65QvsBHlDmg',
        'TENANT':         '8B8039C92C3E4EDFAB97CE576492D70C'
      }
    })
    .success(function(data) {
      debugger
    })
    .error(function(data, status) {
      debugger
    });
    //console.log('getAssets::', getAssets)
  }
}

apmSampleAppCtrl.$inject = ['$scope', '$rootScope', '$state', '$timeout', 'ApmSampleAppService', 'getAssets', '$http'];
export default apmSampleAppCtrl;
