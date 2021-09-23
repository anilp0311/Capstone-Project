const url = 'http://localhost:8010/proxy/v4/games';//need to change this back to original api url
const options = {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Authorization' : 'Bearer ' + BEARER,
        'Client-ID' : CLIENT_ID,

    },
    body : "fields *; where first_release_date > 1577836800 & multiplayer_modes != null; limit 100;"

};
fetch(url, options)
    .then(function (data){
       data.json().then(function(json){
           console.log(json)
           for(let game of json){
               console.log(`INSERT INTO games(title, art_cover, critics_rating, description) VALUES ('${game.name}','${game.cover}','${game.total_rating}','${game.summary}')`)
           }
       })
    })
    .catch();
// const coverUrl = "http://localhost:8010/proxy/v4/covers";
// const coverOptions = {
//     method: 'POST',
//     headers: {
//         'Content-Type': 'application/json',
//         'Authorization': 'Bearer' + BEARER,
//         'Client-ID': CLIENT_ID ,
//     },
//     data: "fields game,height,image_id,url,width;"
// }

// fetch(coverUrl, coverOptions)
//     .then(function (data){
//         data.json().then(function(json){
//             console.log(json)
//             for(let game of json){
//                 console.log(`INSERT INTO games(art_cover) VALUES('${game.art_cover}'`)
//             }
//         })
// })
//     .catch(err => {
//         console.error(err);
//     });
