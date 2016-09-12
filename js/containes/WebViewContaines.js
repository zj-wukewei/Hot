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
  const {news} = state;
  return {
    news
  }
}

export default connect(mapStateToProps)(WebContainer);