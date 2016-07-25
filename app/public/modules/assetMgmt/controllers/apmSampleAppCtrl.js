class apmSampleAppCtrl {
  constructor($scope, $rootScope, $state, $timeout, ApmSampleAppService, getAssets, $http) {
    $rootScope.tenantInfo = null;
    $rootScope.userPreferenceForAssetName = null;
    $scope.assets = getAssets;
    $scope.selected = "tab1";
  }
}

apmSampleAppCtrl.$inject = ['$scope', '$rootScope', '$state', '$timeout', 'ApmSampleAppService', 'getAssets', '$http'];
export default apmSampleAppCtrl;
