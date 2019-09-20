	<%@ page pageEncoding="UTF-8" %>
    <section class="about py-lg-5 py-md-5 py-5">
        <div class="container">
            <div class="inner-sec-w3pvt py-lg-5 py-3">
	        	<a name="list"></a>
            	<h3 class="tittle  text-center my-lg-5 my-3">Our Tips</h3>
                <div class="feature-grids row mt-3 mb-lg-5 mb-3 mt-lg-5 text-center">
                    <div class="col-lg-4" data-aos="fade-up">
                        <div class="bottom-gd px-3">
                            <span class="fa fa-refresh fa-spin" aria-hidden="true"></span>
                            <h3 class="my-4"> Collecting places</h3>
                            <p>Please wait. We are collecting the best places near to you. Your browser will be automatically redirected.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
    <script type="text/javascript">    
    // ${info.ip}
    $(document).ready(function() {
        checkLocationIsLoaded('${info.location}');    	
    });
    </script>
