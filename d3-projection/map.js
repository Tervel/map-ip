var arcs = new Datamap({
    element: document.getElementById("basic"),
    projection: 'mercator',
});

arcs.arc([
    {
        origin: {
            latitude: 40.639722,
            longitude: -73.778889
        },
        destination: {
            latitude: 37.618889,
            longitude: -122.375
        }
    },
    {
        origin: {
            latitude: 30.194444,
            longitude: -97.67
        },
        destination: {
            latitude: 25.793333,
            longitude: -80.290556
        },
        options: {
            strokeWidth: 2,
            strokeColor: 'rgba(100, 10, 200, 0.4)',
            greatArc: true
        }
    },
    {
        origin: {
            latitude: 39.861667,
            longitude: -104.673056
        },
        destination: {
            latitude: 35.877778,
            longitude: -78.7875
        }
    }
],  {strokeWidth: 1, arcSharpness: 1.4});
