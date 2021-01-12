const BASE_URL = 'http://61.185.20.20:2300/product-xczx';
/**
 * @function 封装ajax默认请求参数,前提是先要引入axios
 * @param {object} options 请求参数
 */
function HttpRequest(option) {
  // 默认配置
  let instance = axios.create({
    baseURL: BASE_URL,
    timeout: 10000
  });
  // 根据传参动态修改默认配置
  // 传递token
  if (option.token) {
    instance.defaults.headers.common['Authorization'] = option.token;
  }
  // 添加请求拦截器
  instance.interceptors.request.use(
    function(config) {
      console.log('###################################');
      console.log('请求链接:', config.url);
      console.log('请求方式:', config.method);
      console.log('请求参数:', config.params);
      return config;
    },
    function(error) {
      alert(error)
      return Promise.reject(error);
    }
  );
  // 添加响应拦截器
  instance.interceptors.response.use(
    function(response) {
      console.log('------------------------');
      console.log('响应数据:', response.data);
      console.log('###################################');
      return response;
    },
    function(error) {
      alert(error)
      return Promise.reject(error);
    }
  );

  return instance;
}
/**
 * 全局请求变量
 */
const http = {
  /**
   * @function 封装get请求
   * @param {object} options 请求参数
   */
  get: option => {
    let httpRequest = new HttpRequest({
      type: 'GET',
      url: option.url,
      params: option.params,
      token: option.token
    });
    return httpRequest.request({
      url: option.url,
      params: option.params
    });
  },
  /**
   * @function 封装post请求
   * @param {object} options 请求参数
   */
  post: option => {
    let httpRequest =new HttpRequest({
      type: 'POST',
      url: option.url,
      data: option.data,
      token: option.token
    });
    return httpRequest.request({
        url: option.url,
        params: option.params
    });
  }
};
// 测试
// async getData() {
//   let {data} = await http.get({
//     url: '/poorList/listPoorChart',
//     params: {
//       areaId: 6104
//     }
//   })
// }
