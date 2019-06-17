<%@ page contentType="text/html;charset=UTF-8" 
	language="java" 
	import="accountmanager.*, bd.dbos.*, javax.mail.*, java.util.*" %>

<%
	AccountManager acc = (AccountManager)session.getAttribute("user");

    if (acc == null)
        response.sendRedirect("../");
%>

<html class="loading" lang="en" data-textdirection="ltr">
  <!-- BEGIN: Head-->
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">

    <title>WhaleMail</title>

    <link href="../assets/icon" rel="stylesheet">

    <!-- BEGIN: VENDOR CSS-->
    <link rel="stylesheet" type="text/css" href="../css/vendors.min.css">
    <link rel="stylesheet" type="text/css" href="../css/fonts.css">
    <link rel="stylesheet" type="text/css" href="../css/flag-icon.min.css">
    <!-- END: VENDOR CSS-->

    <!-- BEGIN: Page Level CSS-->
    <link rel="stylesheet" type="text/css" href="../css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <link rel="stylesheet" type="text/css" href="../css/app-sidebar.css">
    <link rel="stylesheet" type="text/css" href="../css/app-email.css">
    <!-- END: Page Level CSS-->

    <!-- BEGIN: Custom CSS-->
    <link rel="stylesheet" type="text/css" href="../css/custom.css">
    <!-- END: Custom CSS-->

  <!-- END: Head-->
  </head>

  <body class="vertical-layout vertical-menu-collapsible page-header-dark vertical-modern-menu 2-columns app-page" data-open="click" data-menu="vertical-modern-menu" data-col="2-columns">
    <!-- BEGIN: Page Main-->
    <div id="main">
      <div class="row">
        <div class="content-wrapper-before gradient-45deg-indigo-purple"></div>
        <div class="col s12">
          <div class="container">

            <!-- Sidebar Area Starts -->
            <div class="sidebar-left sidebar-fixed">
              <div class="sidebar">
                <div class="sidebar-content">
                  <div class="sidebar-header">
                    <div class="sidebar-details">
                      <h5 class="m-0 sidebar-title">
                        <i class="material-icons app-header-icon text-top">mail_outline</i> WhaleMail
                      </h5>
                      <div class="row valign-wrapper mt-10 pt-2 animate fadeLeft">
                        <div class="col s12">
                          	<p class="m-0 subtitle font-weight-700">
							<% 
								try {
									out.println(acc.getUser().getUser()); 
								}
								catch(Exception ex) {
									out.println(ex.getMessage());
								}
							%>
                          	</p>
                          	<p class="m-0 text-muted">
							<% 
								try {
									out.println(acc.getCurrentAccount().getUser()); 
								}
								catch(Exception ex) {
									out.println(ex.getMessage());
								}
							%>
							</p>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div id="sidebar-list" class="sidebar-menu list-group position-relative animate fadeLeft ps ps--active-y">
                    <div class="sidebar-list-padding app-sidebar" id="email-sidenav">
                      <ul class="email-list display-grid">
                        <li class="sidebar-title">Folders</li>
						<%
							// Listagem de pastas
							
							try {
								Folder[][] folders = acc.getCurrentFolders();
								
								for (int i = 0; i < folders.length; i++) {
									for (int j = 0; j < folders[i].length; j++) {
										out.println("<li>");
										out.println("<a href=''#' class='text-sub'>");
										out.println(folders[i][j].getName());
										out.println("</a>");
										out.println("</li>");
									}
								}
							} catch (Exception ex) {
								out.println("<li>");
								out.println("<a href=''#' class='text-sub'>");
								out.println(ex.getMessage());
								out.println("</a>");
								out.println("</li>");
							}
								
						%>
                      </ul>
                    </div>

                    <div class="ps__rail-x" style="left: 0px; bottom: 0px;">
                      <div class="ps__thumb-x" tabindex="0" style="left: 0px; width: 0px;"></div>
                    </div>

                    <div class="ps__rail-y" style="top: 0px; height: 465px; right: 0px;">
                      <div class="ps__thumb-y" tabindex="0" style="top: 0px; height: 366px;"></div>
                    </div>
                  </div>
                  
                  <a href="#" data-target="email-sidenav" class="sidenav-trigger hide-on-large-only"><i class="material-icons">menu</i></a>
                </div>
              </div>
            </div>
            <!-- Sidebar Area Ends -->

            <!-- Content Area Starts -->
            <div class="app-email">
              <div class="content-area content-right">
                <div class="app-wrapper">
                  <div class="app-search">
                    <i class="material-icons mr-2 search-icon">search</i>
                    <input type="text" placeholder="Search Mail" class="app-filter" id="email_filter">
                  </div>
                  <div class="card card card-default scrollspy border-radius-6 fixed-width">
                    <div class="card-content p-0">
                      <div class="email-header">
                        <div class="left-icons">
                          <span class="header-checkbox">
                            <label>
                              <input type="checkbox" onclick="toggle(this)"><span></span>
                            </label>
                          </span>

                          <span class="action-icons">
                            <i class="material-icons">refresh</i>
                            <i class="material-icons">mail_outline</i>
                            <i class="material-icons">label_outline</i>
                            <i class="material-icons">folder_open</i>
                            <i class="material-icons">info_outline</i>
                            <i class="material-icons delete-mails">delete</i>
                          </span>
                        </div>
                        <div class="list-content"></div>
                        <div class="email-action">
                          <span class="email-options">
                            <i class="material-icons grey-text">more_vert</i>
                          </span>
                        </div>
                      </div>

                      <div class="collection email-collection ps ps--active-y" style="max-height: 410px;">
                        <a href='#' class='collection-item animate fadeUp delay-2'>
                          <div class="list-content">
                            <div class="list-title-area">
                              <div class="user-media">
                                <div class="list-title">Pari Kalin</div>
                              </div>

                              <div class="title-right">
                                <span class="attach-file">
                                  <i class="material-icons">attach_file</i>
                                </span>
                              </div>
                            </div>
                            <div class="list-desc">
                              There are many variations of passages of Lorem Ipsum available, but the majority
                              have suffered alteration in some form, by injected humour, or randomised words which don't look even
                              slightly believable. If you are going to use a passage of Lorem Ipsum
                            </div>
                          </div>
                          <div class="list-right">
                            <div class="list-date"> 12:47 PM </div>
                          </div>
                        </a>
                        <div class="ps__rail-x" style="left: 0px; bottom: -1797px;">
                          <div class="ps__thumb-x" tabindex="0" style="left: 0px; width: 0px;"></div>
                        </div>
                        <div class="ps__rail-y" style="top: 1797px; height: 410px; right: 0px;">
                          <div class="ps__thumb-y" tabindex="0" style="top: 334px; height: 76px;"></div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- Content Area Ends -->

            <!-- Add new email popup -->
            <div style="bottom: 54px; right: 19px;" class="fixed-action-btn direction-top">
              <a class="btn-floating btn-large primary-text gradient-shadow modal-trigger" href="../Logout">
                <i class="material-icons">add</i>
              </a>
            </div>
            <!-- Add new email popup Ends-->

            <!-- Modal Structure -->
            <div id="modal1" class="modal border-radius-6" tabindex="0">
              <div class="modal-content">
                <h5 class="mt-0">New Message</h5>
                <hr>
                <div class="row">
                  <form class="col s12">
                    <div class="row">
                      <div class="input-field col s12">
                        <i class="material-icons prefix"> person_outline </i>
                        <input id="email" type="email" class="validate">
                        <label for="email">Recipients</label>
                      </div>

                      <div class="input-field col s12">
                        <i class="material-icons prefix"> title </i>
                        <input id="subject" type="text" class="validate">
                        <label for="subject">Subject</label>
                      </div>

                      <div class="input-field col s12">
                        <div id="mceu_11" class="mce-tinymce mce-container mce-panel" hidefocus="1" tabindex="-1" role="application" style="visibility: hidden; border-width: 1px; width: 100%;">
                          <div id="mceu_11-body" class="mce-container-body mce-stack-layout">
                            <div id="mceu_12" class="mce-top-part mce-container mce-stack-layout-item mce-first">
                              <div id="mceu_12-body" class="mce-container-body">
                                <div id="mceu_13" class="mce-container mce-menubar mce-toolbar mce-first" role="menubar" style="border-width: 0px 0px 1px;">
                                  <div id="mceu_13-body" class="mce-container-body mce-flow-layout">
                                    <div id="mceu_14" class="mce-widget mce-btn mce-menubtn mce-flow-layout-item mce-first mce-btn-has-text" tabindex="-1" aria-labelledby="mceu_14" role="menuitem" aria-haspopup="true">
                                      <button id="mceu_14-open" role="presentation" type="button" tabindex="-1">
                                        <span class="mce-txt">File</span>
                                        <i class="mce-caret"></i>
                                      </button>
                                    </div>

                                    <div id="mceu_15" class="mce-widget mce-btn mce-menubtn mce-flow-layout-item mce-btn-has-text" tabindex="-1" aria-labelledby="mceu_15" role="menuitem" aria-haspopup="true">
                                      <button id="mceu_15-open" role="presentation" type="button" tabindex="-1">
                                        <span class="mce-txt">Edit</span>
                                        <i class="mce-caret"></i>
                                      </button>
                                    </div>

                                    <div id="mceu_16" class="mce-widget mce-btn mce-menubtn mce-flow-layout-item mce-btn-has-text" tabindex="-1" aria-labelledby="mceu_16" role="menuitem" aria-haspopup="true">
                                      <button id="mceu_16-open" role="presentation" type="button" tabindex="-1">
                                        <span class="mce-txt">View</span>
                                        <i class="mce-caret"></i>
                                      </button>
                                    </div>

                                    <div id="mceu_17" class="mce-widget mce-btn mce-menubtn mce-flow-layout-item mce-last mce-btn-has-text" tabindex="-1" aria-labelledby="mceu_17" role="menuitem" aria-haspopup="true">
                                      <button id="mceu_17-open" role="presentation" type="button" tabindex="-1">
                                        <span class="mce-txt">Format</span>
                                        <i class="mce-caret"></i>
                                      </button>
                                    </div>
                                  </div>
                                </div>
                                <div id="mceu_18" class="mce-toolbar-grp mce-container mce-panel mce-last" hidefocus="1" tabindex="-1" role="group">
                                  <div id="mceu_18-body" class="mce-container-body mce-stack-layout">
                                    <div id="mceu_19" class="mce-container mce-toolbar mce-stack-layout-item mce-first mce-last" role="toolbar">
                                      <div id="mceu_19-body" class="mce-container-body mce-flow-layout">
                                        <div id="mceu_20" class="mce-container mce-flow-layout-item mce-first mce-btn-group" role="group">
                                          <div id="mceu_20-body">
                                            <div id="mceu_0" class="mce-widget mce-btn mce-first mce-disabled" tabindex="-1" role="button" aria-label="Undo" aria-disabled="true">
                                              <button id="mceu_0-button" role="presentation" type="button" tabindex="-1">
                                                <i class="mce-ico mce-i-undo"></i>
                                              </button>
                                            </div>

                                            <div id="mceu_1" class="mce-widget mce-btn mce-last mce-disabled" tabindex="-1" role="button" aria-label="Redo" aria-disabled="true">
                                              <button id="mceu_1-button" role="presentation" type="button" tabindex="-1">
                                                <i class="mce-ico mce-i-redo"></i>
                                              </button>
                                            </div>
                                          </div>
                                        </div>

                                        <div id="mceu_21" class="mce-container mce-flow-layout-item mce-btn-group" role="group">
                                          <div id="mceu_21-body">
                                            <div id="mceu_2" class="mce-widget mce-btn mce-menubtn mce-first mce-last mce-btn-has-text" tabindex="-1" aria-labelledby="mceu_2" role="button" aria-haspopup="true">
                                              <button id="mceu_2-open" role="presentation" type="button" tabindex="-1">
                                                <span class="mce-txt">Formats</span>
                                                <i class="mce-caret"></i>
                                              </button>
                                            </div>
                                          </div>
                                        </div>

                                        <div id="mceu_22" class="mce-container mce-flow-layout-item mce-btn-group" role="group">
                                          <div id="mceu_22-body">
                                            <div id="mceu_3" class="mce-widget mce-btn mce-first" tabindex="-1" aria-pressed="false" role="button" aria-label="Bold">
                                              <button id="mceu_3-button" role="presentation" type="button" tabindex="-1">
                                                <i class="mce-ico mce-i-bold"></i>
                                              </button>
                                            </div>
                                            <div id="mceu_4" class="mce-widget mce-btn mce-last" tabindex="-1" aria-pressed="false" role="button" aria-label="Italic">
                                              <button id="mceu_4-button" role="presentation" type="button" tabindex="-1">
                                                <i class="mce-ico mce-i-italic"></i>
                                              </button>
                                            </div>
                                          </div>
                                        </div>
                                        
                                        <div id="mceu_23" class="mce-container mce-flow-layout-item mce-btn-group" role="group">
                                          <div id="mceu_23-body">
                                            <div id="mceu_5" class="mce-widget mce-btn mce-first" tabindex="-1" aria-pressed="false" role="button" aria-label="Align left">
                                              <button id="mceu_5-button" role="presentation" type="button" tabindex="-1">
                                                <i class="mce-ico mce-i-alignleft"></i>
                                              </button>
                                            </div>
                                            <div id="mceu_6" class="mce-widget mce-btn" tabindex="-1" aria-pressed="false" role="button" aria-label="Align center">
                                              <button id="mceu_6-button" role="presentation" type="button" tabindex="-1">
                                                <i class="mce-ico mce-i-aligncenter"></i>
                                              </button>
                                            </div>
                                            <div id="mceu_7" class="mce-widget mce-btn" tabindex="-1" aria-pressed="false" role="button" aria-label="Align right">
                                              <button id="mceu_7-button" role="presentation" type="button" tabindex="-1">
                                                <i class="mce-ico mce-i-alignright"></i>
                                              </button>
                                            </div>
                                            <div id="mceu_8" class="mce-widget mce-btn mce-last" tabindex="-1" aria-pressed="false" role="button" aria-label="Justify">
                                              <button id="mceu_8-button" role="presentation" type="button" tabindex="-1">
                                                <i class="mce-ico mce-i-alignjustify"></i>
                                              </button>
                                            </div>
                                          </div>
                                        </div>
                                        
                                        <div id="mceu_24" class="mce-container mce-flow-layout-item mce-btn-group" role="group">
                                          <div id="mceu_24-body">
                                            <div id="mceu_9" class="mce-widget mce-btn mce-first" tabindex="-1" role="button" aria-label="Decrease indent">
                                              <button id="mceu_9-button" role="presentation" type="button" tabindex="-1">
                                                <i class="mce-ico mce-i-outdent"></i>
                                              </button>
                                            </div>

                                            <div id="mceu_10" class="mce-widget mce-btn mce-last" tabindex="-1" role="button" aria-label="Increase indent">
                                              <button id="mceu_10-button" role="presentation" type="button" tabindex="-1">
                                                <i class="mce-ico mce-i-indent"></i>
                                              </button>
                                            </div>
                                          </div>
                                        </div>
                                        
                                        <div id="mceu_25" class="mce-container mce-flow-layout-item mce-last mce-btn-group" role="group">
                                          <div id="mceu_25-body"></div>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>

                            <div id="mceu_26" class="mce-edit-area mce-container mce-panel mce-stack-layout-item" hidefocus="1" tabindex="-1" role="group" style="border-width: 1px 0px 0px;">
                              <iframe id="editor_ifr" frameborder="0" allowtransparency="true" title="Rich Text Area. Press ALT-F9 for menu. Press ALT-F10 for toolbar. Press ALT-0 for help" style="width: 100%; height: 100px; display: block;" src="../assets/saved_resource.html"></iframe>
                            </div>

                            <div id="mceu_27" class="mce-statusbar mce-container mce-panel mce-stack-layout-item mce-last" hidefocus="1" tabindex="-1" role="group" style="border-width: 1px 0px 0px;">
                              <div id="mceu_27-body" class="mce-container-body mce-flow-layout">
                                <div id="mceu_28" class="mce-path mce-flow-layout-item mce-first">
                                  <div class="mce-path-item">&nbsp;</div>
                                </div>
                                <div id="mceu_29" class="mce-flow-layout-item mce-resizehandle">
                                  <i class="mce-ico mce-i-resize"></i>
                                </div>
                                <span id="mceu_30" class="mce-branding mce-widget mce-label mce-flow-layout-item mce-last">
                                  Powered by 
                                  <a href="https://www.tiny.cloud/?utm_campaign=editor_referral&amp;utm_medium=poweredby&amp;utm_source=tinymce" rel="noopener" target="_blank" role="presentation" tabindex="-1">
                                    Tiny
                                  </a>
                                </span>
                              </div>
                            </div>
                          </div>
                        </div>

                        <textarea id="editor" style="display: none;" aria-hidden="true">
                          Message
                        </textarea>
                      </div>
                    </div>
                  </form>
                </div>
              </div>
              <div class="modal-footer">
                <a class="btn modal-close waves-effect waves-light mr-2">
                  <i class="material-icons">send</i> Send
                </a>
              </div>
            </div>
            <!-- END RIGHT SIDEBAR NAV -->

          </div>
        </div>
      </div>
    </div>
    <!-- END: Page Main-->

    <!-- BEGIN: Footer-->
    <footer class="page-footer footer footer-static footer-dark gradient-45deg-indigo-purple gradient-shadow navbar-border navbar-shadow">
      <div class="footer-copyright">
        <div class="container"></div>
      </div>
    </footer>

    <!-- END: Footer-->
    <!-- BEGIN VENDOR JS-->
    <script src="../js/vendors.min.js" type="text/javascript"></script>
    <!-- BEGIN VENDOR JS-->
    <!-- BEGIN PAGE VENDOR JS-->
    <script src="../js/jquery-sortable-min.js"></script>
    <script src="../js/tinymce.min.js"></script>
    <script src="../js/jquery.waypoints.min.js"></script>
    <!-- END PAGE VENDOR JS-->
    <!-- BEGIN THEME  JS-->
    <script src="../js/plugins.js" type="text/javascript"></script>
    <script src="../js/custom-script.js" type="text/javascript"></script>
    <script src="../js/customizer.js" type="text/javascript"></script>
    <!-- END THEME  JS-->
    <!-- BEGIN PAGE LEVEL JS-->
    <script src="../js/app-email.js" type="text/javascript"></script>
    <!-- END PAGE LEVEL JS-->

    <div class="material-tooltip">
      <div class="tooltip-content"></div>
    </div>

    <div class="sidenav-overlay" style="display: none;"></div>
    <div class="drag-target"></div>
    <div class="sidenav-overlay"></div>
    <div class="drag-target right-aligned"></div>
    <div class="sidenav-overlay"></div>
    <div class="drag-target right-aligned"></div>
    <div class="sidenav-overlay"></div>
    <div class="drag-target right-aligned"></div>
    <div class="sidenav-overlay"></div>
    <div class="drag-target"></div>
  </body>
</html></html>