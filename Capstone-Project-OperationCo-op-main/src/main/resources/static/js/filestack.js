

const stackClient = filestack.init(FILESTACKKEY);
// const options = {
//     onFileUploadFinished: file => {
//         // If you throw any error in this function it will reject the file selection.
//         // The error message will be displayed to the user as an alert.
//         if (file.size > 1000 * 1000) {
//             throw new Error('File too big, select something smaller than 1MB');
//         }
//     }
// };
// client.picker().open();
//
