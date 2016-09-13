import React, {
  Component
} from 'react';
import {
  StyleSheet,
  Text,
  RefreshControl,
  TouchableOpacity,
  ListView,
  InteractionManager,
  Image,
  ActivityIndicator,
  View
} from 'react-native';
import Header from '../components/Header.js';
import LoadingView from "../components/LoadingView.js";
import WebViewContaines from "../containes/WebViewContaines.js";
import {
  toastShort
} from "../utils/ToastUtil.js";
var page = 1;
var loadMoreTime = 0;
class News extends Component {

  constructor(props) {
    super(props);
    this.renderContent = this.renderContent.bind(this);
    this.renderItem = this.renderItem.bind(this);
    this.renderFooter = this.renderFooter.bind(this);
    this.onPress = this.onPress.bind(this);
    this.state = {
      dataSource: new ListView.DataSource({
        rowHasChanged: (r1, r2) => r1 !== r2
      })
    };
  }


  componentDidMount() {
    const {
      actions
    } = this.props;
    actions.fetchNews(true, false, false, page);
  }

  onEndReached() {
    let time = Date.parse(new Date()) / 1000;
    if (time - loadMoreTime > 1) {
      const {
        actions
      } = this.props;
      page++;
      actions.fetchNews(false, false, true, page);
      loadMoreTime = Date.parse(new Date()) / 1000;
    }

  }

  renderItem(news) {
    return (
      <TouchableOpacity onPress={() => this.onPress(news.title, news.url) }>
        <View style={styles.containerItem}>
          <Image style= {styles.image} source={{ uri: news.picUrl }}/>
          <View style={{ flex: 1, flexDirection: 'column' }} >
            <Text style={styles.title}>
              {news.title}
            </Text>
            <Text >
              来自: {news.description}
            </Text>
          </View>
        </View>
      </TouchableOpacity>
    )
  }

  onPress(title, url) {
    const { navigator } = this.props;
    navigator.push({
      component: WebViewContaines,
      name: 'WebViewContaines',
      url: url,
      title: title
    });
  }

  renderFooter() {
    const {
      news
    } = this.props;
    if (news.isLoadMore) {
      return (
        <View style={styles.footerContainer}>
          <ActivityIndicator size="small" color="#FF4081" />
          <Text style={styles.footerText}>
            数据加载中……
          </Text>
        </View>
      )
    }
  }
  onRefresh() {
    const {
      actions
    } = this.props;
    actions.fetchNews(false, true, false, page);
  }
  renderContent() {
    const {
      news
    } = this.props;
    if (news.loading) {
      return <LoadingView />
    }

    return (
      <ListView initialListSize = {1}
        dataSource = {this.state.dataSource.cloneWithRows(news.newsList) }
        renderRow = {this.renderItem}
        style = {styles.listView}
        onEndReached = {this.onEndReached.bind(this) }
        onEndReachedThreshold = {10}
        renderFooter = {this.renderFooter}
        refreshControl = {
          <RefreshControl
            style={{ backgroundColor: 'transparent' }}
            refreshing={news.isRefresh}
            onRefresh={this.onRefresh.bind(this) }
            title="Loading..."
            colors={['#ff0000', '#00ff00', '#0000ff', '#3ad564']}
            />
        }
        />)
  }

  render() {
    const {
      navigator,
      news
    } = this.props;
    return (
      <View style={styles.container}>
        <Header title="体育" navigator= {navigator}/>
        {this.renderContent() }
      </View>

    )
  }

}



const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5FCFF',
  },
  containerItem: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fcfcfc',
    padding: 10,
    borderBottomColor: '#ddd',
  },
  image: {
    width: 88,
    height: 66,
    marginRight: 10
  },
  title: {
    fontSize: 18,
    textAlign: 'left',
    color: 'black'
  },
  footerContainer: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    padding: 5
  },
  footerText: {
    textAlign: 'center',
    fontSize: 16,
    marginLeft: 10
  }
});

export default News;