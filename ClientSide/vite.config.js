import { defineConfig, loadEnv } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), '');
  return {
    define: {
      'process.env.API_URL': JSON.stringify(env.ENV == "development" ? "http://localhost:8080" : "http://172.17.0.148:8073")
    },
    plugins: [react()],
    server: {
      watch: {
        usePolling: true,
      },
      host: true, 
      strictPort: true,
      port: 5173, 
    }
  }
})
