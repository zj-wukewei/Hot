import React, {
  Component
} from 'react';
import {
  connect
} from 'react-redux';
import WebViewPage from "../pages/WebViewPage.js";
class WebContainer extends Component {

	render() {
     return (
       <WebViewPage {...this.props} />
    );
  }

}

function mapStateToProps(state) {
  const {home} = state;
  return {
    home
  }
}

export default connect(mapStateToProps)(WebContainer);