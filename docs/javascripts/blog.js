var notoNaskhRegular = new FontFaceObserver("Noto Naskh Arabic", { weight: 400 });
var notoNaskhBold    = new FontFaceObserver("Noto Naskh Arabic", { weight: 700 });
var notoKufiRegular  = new FontFaceObserver("Noto Kufi Arabic",  { weight: 400 });
var notoKufiBold     = new FontFaceObserver("Noto Kufi Arabic",  { weight: 700 });
var kawkabMono       = new FontFaceObserver("Kawkab Mono",       { weight: 400 });

var arabicTest = "اهلا";
var fontLoadTimeout = 5000;

Promise.all([
  notoNaskhRegular.load(arabicTest, fontLoadTimeout),
  notoNaskhBold.load(arabicTest, fontLoadTimeout)
]).then(function() {
  document.documentElement.className += " naskh-font-loaded";
}, function() {
  console.log("Could not load Noto Naskh Arabic font");
});

Promise.all([
  notoKufiRegular.load(arabicTest, fontLoadTimeout),
  notoKufiBold.load(arabicTest, fontLoadTimeout)
]).then(function() {
  document.documentElement.className += " kufi-font-loaded";
}, function() {
  console.log("Could not load Noto Kufi Arabi font");
});

Promise.all([
  kawkabMono.load(arabicTest, fontLoadTimeout)
]).then(function() {
  document.documentElement.className += " kawkab-font-loaded";
}, function() {
  console.log("could not load KawkabMono font");
});
