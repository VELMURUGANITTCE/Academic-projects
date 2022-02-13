<?php
$connection = oci_connect('system','Jaga12345','localhost/DuJa');
If (!$connection) {
    echo "oops, Database not connected to server!";
}
else {
}
oci_close($connection);
?>