

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

    let formData1 = new FormData();
    formData1.append("groupId", groupId);
    formData1.append("url", filedata.url);
    fetch("/groups/pic", {
        method:"POST",
        body:formData1,
    }).catch(err => { console.log(err) });
}


//jquery
$(document).ready(function() {
    //filestack
    console.log(url);
    $("#profile-btn2").click(function(){
        stackClient.picker(options).open();
    });
})