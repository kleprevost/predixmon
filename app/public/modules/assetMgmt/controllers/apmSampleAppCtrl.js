class apmSampleAppCtrl {
  constructor($scope, $rootScope, $state, $timeout, ApmSampleAppService, getAssets) {
    $rootScope.tenantInfo = null;
    $rootScope.userPreferenceForAssetName = null;
    $scope.assets = getAssets;
    $scope.selected = "tab1";

    //console.log('getAssets::', getAssets)
  }
}

apmSampleAppCtrl.$inject = ['$scope', '$rootScope', '$state', '$timeout', 'ApmSampleAppService', 'getAssets'];
export default apmSampleAppCtrl;
