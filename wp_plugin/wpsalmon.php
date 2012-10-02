<?php
/**
 * @package WpSalmon
 * @version 0.1.0
 */
/*
Plugin Name: WpSalmon
Plugin URI: http://balitechy.com
Description: Small plugin that allow you to track your Wp Ecommerce order via Android devices.
Author: Eka Putra
Version: 0.1.0
Author URI: http://balitechy.com/
*/
$SHOP_ID = '998574'; // next will be the API key
$STATUS_ERR = 'error';
$STATUS_OK = 'ok';

$GET_ORDERS = 'get_orders';
$ORDER_DETAIL = 'order_detail';
$CUST_DETAIL = 'cust_detail';

/*============================= Global Utility ================================*/
//Format date
function format_date( $timestamp_str ) {
	$format = 'M d, Y H:i';
	$timestamp = (int) $timestamp_str;
	$full_time = date( $format, $timestamp );
	$time_diff = time() - $timestamp;
	if ( $time_diff > 0 && $time_diff < 24 * 60 * 60 )
		$h_time = $h_time = sprintf( '%s ago' , human_time_diff( $timestamp ) );
	else
		$h_time = date( get_option( 'date_format', $format ), $timestamp );
	return $h_time;
}

//To JSON
function json($data){
    return json_encode($data);
}

//Error generator
function return_error($code){
    global $STATUS_ERR, $STATUS_OK;
    
    switch($code){
        case 1:
            $error = array('status'=>$STATUS_ERR, 'message'=>'Invalid access.');
            break;
        default:
            $error = array('status'=>$STATUS_ERR, 'message'=>'No Access.');
    }
    return $error;
}


//Token validator
function validate_token($request){
    global $SHOP_ID;
    if(isset($_GET['token']) && $_GET['token']== $SHOP_ID){
        return true;
    }
    return false;
}


/*======================= WPEcommerce Specific codes ==========================*/
// Get Order currency
function get_currency(){
    global $wpdb;
    $currency_type = get_option( 'currency_type' );
    $query = "SELECT code FROM wp_wpsc_currency_list WHERE id={$currency_type}";
    return $wpdb->get_var($query);
}

// Get Order Status
function get_status($status){
	$status_labels = array(
		1 => 'Incomplete Sale',
		2 => 'Order Received',
		3 => 'Accepted Payment',
		4 => 'Job dispatched',
		5 => 'Closed Order',
		6 => 'Payment Declined'
	);
	
	foreach($status_labels as $key=>$val){
	    if($status==$key){
	        return $val;
	    }
	}
	return 'Status unknown';
	
}

//Get all orders
function get_orders(){
    global $wpdb;
    $final_result = array();
    $query = "SELECT pl.id, pl.totalprice, pl.date, pl.processed 
            FROM wp_wpsc_purchase_logs as pl 
            ORDER BY pl.id DESC 
            LIMIT 0 , 30";
                
    $results = $wpdb->get_results($query);
    for($i=0; $i< count($results); $i++){
        $final_result[] = array(
            'order_id'=> $results[$i]->id,
            'order_total'=> number_format( $results[$i]->totalprice, 2, '.' , ',') ,
            'order_date'=> format_date($results[$i]->date),
            'order_status'=>get_status($results[$i]->processed)
        ); 
    }
    return $final_result;
}

//Get Order detail
function get_order_detail($order_id){
    global $wpdb;
    $final_result = array();
    $query = "SELECT * FROM wp_wpsc_cart_contents WHERE purchaseid=$order_id";
    return array();
}


/* ====================== API Request Handler ================================*/
//Get All Orders
if(isset($_GET['wpsl']) && $_GET['wpsl']== $GET_ORDERS){
    if(validate_token($_GET)){
        $resp = array(
            'status'=> $STATUS_OK,
            'currency'=>get_currency(),
            'data'=> get_orders()
        );
        die(json($resp));
    }else{
        die(json(return_error(1)));
    }
}

//Get Order Detail
if(isset($_GET['wpsl']) && $_GET['wpsl']== $ORDER_DETAIL && isset($_GET['oid']) && (int)$_GET['oid'] != 0){
    if(validate_token($_GET)){
        $resp = array(
            'status'=> $STATUS_OK,
            'currency'=>get_currency(),
            'data'=> get_order_detail((int)$_GET['oid'])
        );
        die(json($resp));
    }else{
        die(json(return_error(1)));
    }
}

?>
