$(function() {
    "use strict";
    // Dashboard 1 Morris-chart
    Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: '2021-02',
            Total: 500000
        }, {
            period: '2021-03',
            Total: 1300000
        }],
        xkey: 'period',
        ykeys: ['Total'],
        labels: ['Total'],
        pointSize: 0,
        fillOpacity: 0,
        pointStrokeColors: ['#f62d51', '#7460ee', '#009efb'],
        behaveLikeLine: true,
        gridLineColor: '#f6f6f6',
        lineWidth: 1,
        hideHover: 'auto',
        lineColors: ['#009efb', '#7460ee', '#009efb'],
        resize: true
    });

});