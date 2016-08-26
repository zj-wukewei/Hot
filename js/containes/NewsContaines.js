'use strict';

import React, {
  Component
} from 'react';
import {
  connect
} from 'react-redux';
import News from "../pages/News.js";
import {
  bindActionCreators
} from 'redux';
import * as newsAction from '../actions/newsList.js';
class NewsContainer extends Component {

  render() {
    return (
      <News {...this.props} />
    );
  }
}

function mapStateToProps(state) {
  const {
    news
  } = state;
  return {
    news
  }
}

function mapDispatchToProp(dispatch) {
  return {
    actions: bindActionCreators(newsAction, dispatch)
  };
}

export default connect(mapStateToProps, mapDispatchToProp)(NewsContainer);