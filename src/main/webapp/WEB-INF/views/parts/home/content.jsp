	<%@ page pageEncoding="UTF-8" %>
    <section class="about py-lg-5 py-md-5 py-5">
        <div class="container">
            <div class="inner-sec-w3pvt py-lg-5 py-3">
	        	<a name="list"></a>
            	<h3 class="tittle  text-center my-lg-5 my-3">Our Tips</h3>
                <div class="feature-grids row mt-3 mb-lg-5 mb-3 mt-lg-5 text-center">
                    <div class="col-lg-12" data-aos="fade-up">
                        <div class="bottom-gd px-3">                            
                            <c:choose>
                                <c:when test="${!empty info.location}">
                                    <span class="fa fa-refresh fa-spin" aria-hidden="true"></span>
                                    <h3 class="my-4"> Collecting places</h3>
                                    <p>Please wait. We are collecting the best places near to you. Your browser will be automatically redirected.</p>
                                </c:when>
                                <c:otherwise>
                                    <span class="fa fa-frown-o" aria-hidden="true"></span>
                                    <h3 class="my-4"> Error</h3>
                                    <p>Sorry, we were not able to find your location according to your IP address.</p>
                                </c:otherwise>                            
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
    <c:if test="${!empty info.location}">
    <script type="text/javascript">    
    // ${info.ip}
    $(document).ready(function() {
        checkLocationIsLoaded('${info.location}');    	
    });
    </script>
    </c:if>
