import axios from "axios";

export default axios.create({
    baseURL:'http://localhost:8080',
    headers:{
        'Content-Type':'application/json',
    },
    withCredentials : true // CORS + login // 없으면 session id 가 날라가는데 항상 다른 session id
})