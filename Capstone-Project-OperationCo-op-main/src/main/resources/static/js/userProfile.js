

//File stack Api
//------starting functionality for profile page
const options = {
    onUploadDone : updateImage ,
    accept: 'image/*',
    maxSize: 10* 1000* 1000,
    uploadInBackground: false
}

function updateImage(result){
    const filedata = result.filesUploaded[0];
    console.log(filedata);
    // $("#profileUrl").val(filedata.url);
    $(".imgP").attr("src",filedata.url);

    let formData = new FormData();
    formData.append("userId", userId);
    formData.append("url", filedata.url);
    fetch("/profile/pic", {
        method:"POST",
        body:formData,
    }).catch(err => { console.log(err) });

}


//jquery functionality
$(document).ready(function() {
    //filestack
    console.log(url);
    $("#profile-btn").click(function(){
        stackClient.picker(options).open();
    });

})
