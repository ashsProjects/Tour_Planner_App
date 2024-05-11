export function SaveTrip(tripName, fileText){
    const file = new Blob([fileText], { type: "application/json" });
    const link = document.createElement("a");
    const url = URL.createObjectURL(file);
    link.href = url;
    link.download = tripName + ".json";
    document.body.appendChild(link);
    link.click();
    
    setTimeout(function() {
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
    }, 0);
}

export function SaveTripKML(tripName, fileText) {
    let kmlContent = `<?xml version="1.0" encoding="UTF-8"?><kml xmlns="http://www.opengis.net/kml/2.2"><Document> <name>Places</name>`;

    fileText.forEach(place => {
        const {streetAddress,latitude,longitude,municipality,region,country,postcode,defaultDisplayName} = place;
        kmlContent += `<Placemark><name>${defaultDisplayName}</name><description><![CDATA[<p>Address: ${streetAddress}, ${municipality}, ${region}, ${country}, ${postcode}</p><p>Latitude: ${latitude}</p><p>Longitude: ${longitude}</p>]]></description><Point><coordinates>${longitude},${latitude},0</coordinates></Point></Placemark>`;
    });

    kmlContent += `</Document></kml>`;

    const kmlBlob = new Blob([kmlContent], { type: 'application/vnd.google-earth.kml+xml' });
    const url = URL.createObjectURL(kmlBlob);
    const link = document.createElement('a');
    link.href = URL.createObjectURL(kmlBlob);
    link.download = tripName + ".kml";
    document.body.appendChild(link);
    link.click();

    setTimeout(function() {
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);
    }, 0);
}